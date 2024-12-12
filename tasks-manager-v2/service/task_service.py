import redis
from model.task import Task
from datetime import datetime
from dotenv import load_dotenv
import os
from exceptions.bad_request_error import BadRequestError
from config.logging_config import logger
from opentelemetry import trace

load_dotenv()
tracer = trace.get_tracer("task-manager-v2")

class TaskManager:
    # set redis_host as host.docker.internal if redis is running locally and container service
    def __init__(self, redis_host=os.getenv("REDIS_HOST"), redis_port=int(os.getenv("REDIS_PORT"))):
        # Connect to Redis server
        self.pool = redis.ConnectionPool(host=redis_host, port=redis_port, db=0, max_connections=int(os.getenv("MAX_CONN")), decode_responses=True)
        self.redis_client = redis.Redis(connection_pool=self.pool)

    def add_task(self, description, priority='medium', due_date=None):
        if (not description):
            raise BadRequestError("Description is required field")
        # Generate task ID, auto-incremented, by redis incr
        task_id = self.redis_client.incr('task_id')
        # Create task object
        task = Task(task_id, description, priority=priority, due_date=due_date)
        # Store task in Redis hash, key is f'task:{task_id}'
        with tracer.start_as_current_span("add tasks") as span:
            self.redis_client.hset(f'task:{task_id}', mapping=task.to_dict())
        logger.info(f'Task added with ID: {task_id}')
        return task_id

    def view_tasks(self, status=None, priority=None):
        # Get all tasks from Redis, Note: all keys starting with 'task:'
        task_ids = self.redis_client.keys('task:*')
        # If no tasks available, print message and return
        # e.g. No tasks available.
        if not task_ids:
            logger.info("No tasks available.")
            return []
        # Iterate over all tasks and print task details
        # e.g. ID: 1, Description: Task 1, Status: pending, Priority: medium, Due Date: 2021-12-31
        tasks = []
        for task_id in task_ids:
            task_info = self.redis_client.hgetall(task_id)
            if status and task_info['status'] != status:
                continue
            if priority and task_info['priority'] != priority:
                continue
            task = Task(task_id.split(':')[1], task_info['description'], task_info['status'],
                         task_info['priority'], datetime.strptime(task_info['due_date'], '%Y-%m-%d').date() if task_info['due_date'] else None)
            tasks.append(task.to_dict())
        
        return tasks
    
    def get_task(self, task_id):
        with tracer.start_as_current_span("fetch from redis") as span:
            task_info = self.redis_client.hgetall(f'task:{task_id}')
        if not task_info:
            raise ValueError(f'Task {task_id} not found.')
        task = Task(task_id, task_info['description'], task_info['status'],
                    task_info['priority'], datetime.strptime(task_info['due_date'], '%Y-%m-%d').date() if task_info['due_date'] else None)
        return task.to_dict()

    def remove_task(self, task_id):
        # Try to delete task, if successful, print message
        # e.g. Task 1 removed.
        # If task not found, print message
        # e.g. Task 1 not found.
        with tracer.start_as_current_span("delete from redis") as span:
            if self.redis_client.delete(f'task:{task_id}'):
                return ""
            else:
                raise ValueError(f'Task {task_id} not found.')

    def complete_task(self, task_id):
        # Check if task exists, if exist, mark task as completed, by hexists and hset
        # e.g. Task 1 marked as completed.
        # If task not found, print message
        # e.g. Task 1 not found.
        with tracer.start_as_current_span("complete task") as span:
            if self.redis_client.hexists(f'task:{task_id}', 'description'):
                self.redis_client.hset(f'task:{task_id}', 'status', 'completed')
                return ""
            else:
                raise ValueError(f'Task {task_id} not found.')

    def redis_pool_info(self):
        return {
            "max_connections": self.pool.max_connections,
            "in_use_connections": len(self.pool._in_use_connections),
            "available_connections": len(self.pool._available_connections)
        }
