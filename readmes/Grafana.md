![Grafana Logo](../Images/grafana_logo.png)

# What is Grafana?
Grafana enables you to query, visualiza, alert on and explore metrics, logs and traces from different data sources. It enables us to query and visualize:
- Time series databases like [Prometheus](./Prometheus.md) and CloudWatch
- Logging tools like [Loki](./Loki.md) and ElasticSearch
- NoSQL/SQL databases
- CI/CD tools like GitHub

Grafana provides tools to display live data on dashboard with insightful graphs and visualization.

Grafana provides a [Grafana Play](https://play.grafana.org/d/bdnahipisghdsa/getting-started-with-grafana-play?orgId=1&from=now-1h&to=now&timezone=browser), which is a sandbox for testing and learning Grafana that runs on Grafana cloud.

# Why Grafana?
- Open Source
- Data visualization
- Multiple data sources support
- Grafana Enterprise
- Metrics, logs and traces monitoring
- Alerting

# Tools Required
- Docker

# Setup Grafana
1. Build all the services <br />
`./build_add.sh`
2. Start the services including Prometheus, Loki, Grafana and Alertmanager <br />
`./startall.sh`
3. Go to [http://localhost:3110](http://localhost:3110) to access Grafana <br />
4. Login using **admin/admin** as username and password <br />
5. Skip resetting password <br />

# Connect to Prometheus
1. Login to [Grafana](http://localhost:3110) <br />
2. Go to **Connection** and select **Add new connection** <br />
![Grafana Data Sources](../Images/grafana_data_sources.png)

3. Search for **Prometheus** and select it <br />
![Grafana Prometheus Data Source](../Images/grafana_prometheus.png)

4. Click **Add new data source** at the top <br />
5. In the Connection section, set **Prometheus server url** as `http://prom:9090` <br />
![Grafana Prometheus Connection](../Images/grafana_prometheus_connection.png)

6. Click **Save & test** and you should see below message <br />
![Grafana Prometheus Connection test](../Images/grafana_prometheus_connection_test.png)

## Explore Prometheus Data: Option 1
1. Go to **Data Sources** <br />
![Data Sources](../Images/grafana_data.png)

2. Click **Explore** for the Prometheus data source <br />
3. Enter a PromQL query. Sample queries could be found [here](./PrometheusQueries.md). <br />
![Grafana Prometheus Query](../Images/grafana_prom_query.png)

## Explore Prometheus Data: Option 2
1. Go to **Explore** tab and select **Metrics** <br />
2. Click **New metric exploration** <br />
![Explore](../Images/explore_metric_dashboard.png)

3. Select the data source (here prometheus) and look at different metrics visualization <br />
![Explore Dashboard](../Images/explore_metric.png)

4. Select a metric to visualize <br />
![Metric](../Images/metric.png)

5. (Optional) Open in Explore to know the query. Click the compass logo <br />
![Explore Option](../Images/explore_option.png)

# Connect to Loki
1. Login to [Grafana](http://localhost:3110) <br />
2. Go to **Connection** and select **Add new connection** (Similar to Step 2 in [Prometheus setup](#connect-to-prometheus)) <br />
3. Search for **Loki** and select it <br />
![Loki Data Source](../Images/loki.png)
4. Click **Add new data source** at the top <br />
5. In the Connection section, set **Prometheus server url** as `http://loki:3100` <br />
![Loki URL](../Images/loki_url.png)

6. Click **Save & test** and you should see below message <br />
![Loki connect](../Images/loki_connection.png)

## Explore Loki Data
1. Go to Data Sources and select **Explore** for **Loki** data source <br />
![Loki Source](../Images/loki.png)

2. Enter a LogQL query in the code builder <br />
`{service_name="grocery-v1"} |= ""` <br />
![LogQL](../Images/logql.png) <br /> <br />
![Logs](../Images/logs.png)

3. More LogQL queries can be found [here](./LokiQueries.md)

# Visualize Dashboard
## Prometheus data source Dashboard
1. Go to Dashboard and click **Create Dashboard** <br />
![Dashboard](../Images/dashboard.png)

2. Click **Add visualization** <br />
![Visualization](../Images/visualization.png)

3. Select a data source <br />
![dashboard sources](../Images/dashboard_sources.png)

4. In Query A, select data source as **prometheus** and write below query in the code builder <br />
`increase(http_server_requests_seconds_count{status="404"}[15m])`
![Prometheus Dashboard](../Images/prometheus_dashboard.png)

5. Add a title and save dashboard <br />
![Save Dashboard](../Images/save_dashboard.png)

## Loki data source Dashboard
1. Select the dashboard created [above](#prometheus-data-source-dashboard) and click **Add** to add a new visualization <br />
2. Select **Loki** as a data source <br />
3. Select visualization as **Logs** (Top right hand side, below Save Dashboard). In the query, add below LogQL <br />
`{level="ERROR", service_name="grocery-v2"} |= ""` <br />
![Loki Dashboard](../Images/loki_query.png)

4. Add a title and save the dashboard <br />
![Dashboard](../Images/final_dashboard.png)

# Connect Alertmanager
1. Go to Data Sources. Click **Add data source** search for **AlertManager** <br />
2. Change Implementaion to **Prometheus** and enter url as `http://alertmanager:9093` <br />
![AlertManager Data Source](../Images/alertmanager_data_source.png)

3. Click **Save & test** <br />
![AlertManager Test](../Images/am_test.png)

4. Go to **Settings** in **Alerting** and enable the alertmanager. Wait for it to get enabled <br />
![Alerting Settings](../Images/alert_settings.png) <br /> <br />
![AlertManager Enabked](../Images/am_enabled.png)

# Create Alerts
1. Go to **Alert Rules** (You should see Promethues alerts configured [here](../alert_rules.yml))
2. Select **New alert rule**
3. Give a name to the rule and select Loki as data source. Add below LogQL <br />
`count_over_time({app="grocery-v2"} [5m] |= "WARN" or "ERROR")` <br />
![Alert Query](../Images/alert_query.png)

4. Select **Reduce** as the alert condition. For Input select A (which represents the above LogQL query) and keep **Function** as **Last** and **Mode** as **Strict** <br />
![Reduce](../Images/reduce.png)

5. Create a new folder to store the rule in **Set Evaluation behavior** <br />
![evaluation folder](../Images/folder.png)

6. Create a new **Evaluation group** <br />
![evaluation reminder](../Images/evaluation.png)

7. Select **Pending Period** as 2 minutes and then Save the alert <br />
![Evaluation Settings](../Images/evaluation_reminder.png)

8. (Optional) If the alert manager is configured on Grafana, then the alerts will be sent to the Slack channel <br />
![Alerts](../Images/alerts.png)
