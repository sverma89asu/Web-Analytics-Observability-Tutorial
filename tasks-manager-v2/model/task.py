class Task:
    def __init__(self, task_id, description, status='pending', priority='medium', due_date=None):
        self.task_id = task_id
        self.description = description
        self.status = status
        self.priority = priority
        self.due_date = due_date

    def to_dict(self):
        return {
            'id': self.task_id,
            'description': self.description,
            'status': self.status,
            'priority': self.priority,
            'due_date': self.due_date.strftime('%Y-%m-%d') if self.due_date else ''
        }

    def __str__(self):
        return (f"ID: {self.task_id}, Description: {self.description}, "
                f"Status: {self.status}, Priority: {self.priority}, "
                f"Due Date: {self.due_date if self.due_date else 'N/A'}")