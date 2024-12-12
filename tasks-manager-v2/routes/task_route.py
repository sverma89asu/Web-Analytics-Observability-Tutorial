from flask import Blueprint, request, jsonify, url_for
from service.task_service import TaskManager
from datetime import datetime
from config.logging_config import logger

tasks = Blueprint('tasks', __name__)

task_manager = TaskManager()

@tasks.route("/tasks", methods = ["POST"])
def add_task():
    logger.info("Received request to create task")
    data = request.get_json()
    description = data.get("description")
    priority = data.get("priority", "medium")
    due_date = data.get("due_date")
    
    if due_date:
        try:
            due_date = datetime.strptime(due_date, '%Y-%m-%d').date()
            task_id = task_manager.add_task(description, priority, due_date)
        except:
            raise ValueError("Invalid date format")
    else:
        task_id = task_manager.add_task(description, priority)
    
    response = jsonify({'task_id': task_id})
    response.status_code = 201
    location_url = url_for("tasks.get_task", task_id=task_id, _external=True)
    response.headers["Location"] = location_url
    return response

@tasks.route("/tasks", methods = ["GET"])
def get_tasks():
    logger.info("Received request to get tasks")
    status = request.args.get("status")
    priority = request.args.get("priority")
    tasks = task_manager.view_tasks(status, priority)
    return jsonify(tasks), 200
    
@tasks.route("/tasks/<int:task_id>", methods = ["GET"])
def get_task(task_id):
    logger.info(f"Received request to get task with id {task_id}")
    task = task_manager.get_task(task_id)
    return jsonify(task), 200

@tasks.route("/tasks/<int:task_id>", methods = ["DELETE"])
def remove_task(task_id):
    logger.info(f"Received request to delete task with id {task_id}")
    return task_manager.remove_task(task_id), 204

@tasks.route("/tasks/<int:task_id>", methods = ["PUT"])
def complete_task(task_id):
    logger.info(f"Received request to update task status for task id {task_id}")
    return task_manager.complete_task(task_id), 200
