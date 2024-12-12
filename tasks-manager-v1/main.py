from flask import Flask
from routes.task_route import tasks
from prometheus_flask_exporter import PrometheusMetrics

app = Flask(__name__)
app.register_blueprint(tasks)

metrics = PrometheusMetrics(app)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8050)
