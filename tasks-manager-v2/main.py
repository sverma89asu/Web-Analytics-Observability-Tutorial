from flask import Flask, jsonify
from redis.exceptions import ConnectionError as RedisConnectionError
from routes.task_route import tasks
from dotenv import load_dotenv
import os
from exceptions.bad_request_error import BadRequestError
from config.logging_config import logger
from prometheus_flask_exporter import PrometheusMetrics

load_dotenv()

app = Flask(__name__)
app.register_blueprint(tasks)

metrics = PrometheusMetrics(app)

@app.errorhandler(ValueError)
def handle_value_error(error):
    logger.error(f"Error occurred. {error}")
    return "", 404

@app.errorhandler(RedisConnectionError)
def handle_connection_error(error):
    logger.error(f"Error occurred. {error}")
    return "", 500

@app.errorhandler(BadRequestError)
def handle_bad_request_error(error):
    logger.error(f"Error occurred. {error}")
    return jsonify({'msg': str(error)}), 400

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=int(os.getenv("FLASK_PORT")), threaded=(os.getenv("THREADED") == "True"))
