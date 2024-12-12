![Prometheus logo](../Images/Prometheus_software_logo.png)

# Table of contents

<span style="font-size: 18px;">[What is Prometheus?](#what-is-prometheus)</span>

<span style="font-size: 18px;">[Why Prometheus?](#why-prometheus)</span>

<span style="font-size: 18px;">[Tools Required](#tools-required)</span>

<span style="font-size: 18px;">[Setup to publish metrics to Prometheus](#setup-to-publish-metrics-to-prometheus)</span>

<span style="font-size: 18px;">[Configure and Start Prometheus using Docker](#configure-and-start-prometheus-using-docker)</span>

<span style="font-size: 18px;">[Configure alerts in Prometheus](#configure-alerts-in-prometheus)</span>

<span style="font-size: 18px;">[Configure AlertManager to Notify alerts on Slack](#configure-alertmanager-to-notify-alerts-on-slack)</span>

# What is Prometheus?
Prometheus is an open-source systems monitoring and alerting tool originally built at SoundCloud in 2012. Prometheus collects and stores its metrics as time series data where the information is stored along with the timestamp at which it was recorded.

# Why Prometheus?
- Open source unlike propritary software like Datadog 
- Ease of use
- Time series data
- Flexible Query Language PromQL
- Multiple modes of Graphing and dashboarding tools
- Alerting

# Tools Required
- Docker
- gradle (to test locally)
- python (to test locally)

# Setup to publish metrics to Prometheus

### Setup in Gradle Service
1. Add prometheus dependency to **build.gradle** <br />
`implementation 'io.micrometer:micrometer-registry-prometheus'`
2. Configure the application to expose the metrics <br />
`management.endpoints.web.exposure.include=metrics,prometheus` <br />
`management.endpoint.prometheus.enabled=true`
3. Start the application using below command <br />
`gradle bootRun`

4. Go to [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus) to access the metrics
![Metrics for Gradle](../Images/spring_boot_prom.png)

### Setup in Flask Service
1. Install the prometheus exporter <br />
`pip3 install prometheus-flask-exporter`
2. Import and add below lines in the **main.py** <br />
`from prometheus_flask_exporter import PrometheusMetrics` <br />
`metrics = PrometheusMetrics(app)`
3. Run the application using below command <br />
`python3 main.py`

![flask main](../Images/flask_main.png) <br />

4. Go to [http://localhost:8050/metrics](http://localhost:8050/metrics) to view the metrics <br />
![flask metrics](../Images/flask_metrics.png) <br />

# Configure and Start Prometheus using Docker
1. Create a yml [file](../prometheus_all.yml) with the configuration as below (Each job_name represents a service. Add/Remove/Modify based on the requirement) <br />
![Prometheus config](../Images/prometheus_config.png)
2. Make sure **Docker Desktop** is running <br />
2. Build all the docker images (All the docker build commands are present in **build_all.sh** and these commands can be run individually on terminal) <br />
`./build_all.sh`
3. Run all services <br />
`./startall.sh`
4. Access Prometheus UI on [http://localhost:9090](http://localhost:9090) <br />
![Prometheus UI](../Images/prom.png)
5. More PromQL queries can be found [here](PrometheusQueries.md).

# Configure alerts in Prometheus
1. Create a rule [file](../alert_rules.yml) in yml for different alert rules <br />
![alert rules](../Images/alert_rules.png)
2. Update [prometheus_all.yml](../prometheus_all.yml) to include rules. Add below configuration. <br />
```
rule_files:
  - /etc/prometheus/alert_rules.yml
```
3. Build and run all the services <br />
`./build_all.sh` <br />
`./startall.sh`
4. Go to [http://localhost:9090/alerts](http://localhost:9090/alerts) to view the alerts created <br />
![Alert on Prometheus](../Images/alert_prom.png)

# Configure AlertManager to Notify alerts on Slack
1. Add a web hook app on slack. Go to **Apps** and browse apps. Search for **web hooks**. Add **Incoming WebHooks** <br />
![Slack App](../Images/slack_1.png) <br /> <br />
![Slack WebHooks](../Images/slack_2.png) <br /> <br />
![Slack WebHooks](../Images/slack_3.png) <br /> <br />
![Slack WebHooks](../Images/slack_4.png) <br /> <br />
![Slack WebHooks](../Images/slack_5.png) <br /> <br />
2. Create a yml file for alert manager <br />
```
global:
  resolve_timeout: 5m

route:
  receiver: 'slack-notifications'

receivers:
  - name: 'slack-notifications'
    slack_configs:
      - api_url: 'https://hooks.slack.com/services/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'
        channel: '#test-channel'
        send_resolved: true
```
3. Add alert manager configuration in prometheus config [file](../prometheus_all.yml)
```
alerting:
  alertmanagers:
    - static_configs:
      - targets:
        - 'alertmanager:9093'
```
4. Make sure to run alertmanger first before running Prometheus. Build and run the services <br />
`./build_all.sh` <br />
`./startall.sh`
5. Go to [http://localhost:9093](http://localhost:9093) to access the alert manager <br />
![Alert Manager](../Images/alert_manager.png)
6. Confirm notification being received for an alert <br />
![Slack Notifications](../Images/slack_message.png)
