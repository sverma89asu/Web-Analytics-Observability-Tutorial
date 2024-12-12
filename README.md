# Web-Analytics-Observability-Tutorial

A comprehensive tutorial for Web Analytics &amp; Observability

# Table of contents

<span style="font-size: 18px;">[Learning outcomes](#learning-outcomes)</span>

<span style="font-size: 18px;">[Overview &amp; History](#overview--history)</span>

<span style="font-size: 18px;">[Learning activities](#learning-activities)</span>

<span style="font-size: 18px;">[References](#references)</span>

<span style="font-size: 18px;">[Resources](#resources)</span>

# Learning outcomes

After finishing this tutorial the student will be able to

## Level 1: Remembering and Understanding

- Define **web analytics** and **observability**, and explain their key concepts.
- Differentiate between web analytics and observability.
- Identify tools commonly used for web analytics and observability.
- Describe the importance of metrics, logs, and traces in observability and their role in system health monitoring.

## Level 2: Applying

- Implement web analytics by configuring tools like **Posthog** to track specific website performance metrics.
- Set up observability tools such as **Prometheus** and **Grafana** to monitor and visualize system health.
- Configure **OpenTelemetry** to collect and analyze trace, metric, and log data from a sample web application.
- Create custom dashboards to visualize key performance indicators (KPIs) relevant to both analytics and observability.

# Overview &amp; History

## Web Analytics

### Definition

Web analytics includes the analysis of the behavior of visitors to a website through the tracking, review, and reporting of data generated by their use of the site and its various components, including webpages, images, and videos. This is to be used for actionable insights that will drive actions to enable the site owner to enhance the usability of the website for users and, ultimately, achieve business objectives of increased traffic, conversions, or sales. [1]

### History [3]

![Web Analytics Evolution](Images/web-analytics-evolution.drawio.svg)

## Observability

### Definition

Observability means the ability to tell what's going on in a system or application through its outputs, logs, and performance metrics. Observability is becoming more and more important in the field of reliability, performance, and security for applications and infrastructure in modern software systems and cloud computing. [2]

### History

![Observability Evolution](Images/observability-evolution.drawio.svg)

# Learning activities

1. [PostHog](./Readmes/Posthog.md)
2. [Jaeger](./Readmes/Jaeger.md)
3. [Prometheus](./readmes/Prometheus.md)
4. [Loki](./readmes/Loki.md)
5. [Grafana](./readmes/Grafana.md)

# Final Thoughts

## PostHog

PostHog [[8]] is a very good tool that helps to understand user behavior and improving product performance. Apart from being an open-source tool that is flexible and capable to capture user interactions automatically. On one note, this aspect of the tool looks very valuable for scaling companies that want to understand their user base. On the other hand, this same feature shows how fragile and important our privacy is. Even upon following the tutorial, if we click on any of our interactions in the Event Explorer window, we find that an absurd amount of our personal information like our IP address, geographical location, as well as device type is captured automatically. If this is the extent of how much of our information is compromised, then we need to ensure more robust and bolder steps are taken for our privacy.

![enter image description here](https://github.com/user-attachments/assets/15eac9ad-2d80-453f-bf93-abe9e6abaa52)

## Jaeger
Jaeger [[9]] is an open source tool which helps us to understand the tracing making it easier to understand the request flows in complex microservice architecture. It provides a powerful visualization of trace data. <br />

The setup of Jaeger in Flask is a bit complex and require a steep learning curve. It also doesn't have built-in alerting system to show to raise alerts and need to be integrated with Prometheus, Loki and Grafana. <br />

## Prometheus
Prometheus [[4]] is an open source tool which helps to collect metrics from different sources. These metrics could be queried with the help of a query language, PromQL, which is easy to use. Alerts can also be configured in Prometheus to monitor exception, errors, bad requests etc. It also supports the integration of AlertManager [[7]] for handling alerts and notifying them. It works very well with Grafana to visualize different dashboards. <br />

The bad part of Prometheus is that different tools will require different exporter to get the metrics published on Promtheus. For example, Spring Boot requires prometheus actuator whereas Flask service requires prometheus-flask-exporter. Apart from these, it provides bunch of exporters like MongoDB, GitHub etc. The metrics published by these exporters could vary, which makes it difficult to remember and write PromQL queries. Also, Prometheus doesn't have built-in authentication which means that anyone would be able to write PromQL queries and get information about the services. <br />

## Loki
Loki [[5]] is another source tool to aggregate logs which can be integrated with Grafana. It uses label based log querying. Logs can be queried with the help of LogQL query language. <br />

LogQL query language doesn't provide lot of options which limits the search capabilities for complex queries. It also relies heavily on Grafana which makes Loki less useful as a stanadlone tool. <br />

## Grafana
Grafana [[6]] is yet another open source tool which offers a range of customizable dashboards for visualizing metrics, logs and traces. It supports the integration of multiple data sources. It also supports alerting capabilities for these different data sources. <br />

Worst part of Grafana is that there are lot of features and data sources in it, which makes it difficult for beginners to setup and grasp knowledge about it. Authentication mechanism has to be handled properly. Grafana also relies on its data sources which means if any of the data source fail, then it won't be able to visualize. <br />

# References

[1]: https://www.techtarget.com/searchbusinessanalytics/definition/Web-analytics
[2]: https://www.redhat.com/en/topics/devops/what-is-observability
[3]: https://contentsquare.com/blog/a-brief-history-of-web-analytics/
[4]: https://prometheus.io/docs/prometheus/latest/getting_started/
[5]: https://grafana.com/oss/loki/
[6]: https://grafana.com/docs/grafana/latest/
[7]: https://prometheus.io/docs/alerting/latest/alertmanager/
[8]: https://posthog.com/tutorials/vue-analytics
[9]: https://www.jaegertracing.io/docs/2.1/

#### 1: [https://www.techtarget.com/searchbusinessanalytics/definition/Web-analytics](https://www.techtarget.com/searchbusinessanalytics/definition/Web-analytics)

#### 2: [https://www.redhat.com/en/topics/devops/what-is-observability](https://www.redhat.com/en/topics/devops/what-is-observability)

#### 3: [https://contentsquare.com/blog/a-brief-history-of-web-analytics/](https://contentsquare.com/blog/a-brief-history-of-web-analytics/)

#### 4: [https://prometheus.io/docs/prometheus/latest/getting_started/](https://prometheus.io/docs/prometheus/latest/getting_started/)

#### 5: [https://grafana.com/oss/loki/](https://grafana.com/oss/loki/)

#### 6: [https://grafana.com/docs/grafana/latest/](https://grafana.com/docs/grafana/latest/)

#### 7: [https://prometheus.io/docs/alerting/latest/alertmanager/](https://prometheus.io/docs/alerting/latest/alertmanager/)

#### 8: [https://posthog.com/tutorials/vue-analytics](https://posthog.com/tutorials/vue-analytics)

#### 9: [https://www.jaegertracing.io/docs/2.1/](https://www.jaegertracing.io/docs/2.1/)

# Resources

#### 1: [https://nodejs.org/en/download/package-manager](https://nodejs.org/en/download/package-manager)
#### 2: [Spring boot with Prometheus](https://www.baeldung.com/spring-boot-prometheus)
#### 3: [Spring boot application with Prometheus and Grafana](https://medium.com/simform-engineering/revolutionize-monitoring-empowering-spring-boot-applications-with-prometheus-and-grafana-e99c5c7248cf)
#### 4: [Spring boot Loki and Grafana logging](https://www.baeldung.com/spring-boot-loki-grafana-logging)
#### 5: [Prometheus Alertmanager](https://medium.com/@krishabh080/prometheus-alert-manager-setup-and-alert-configurations-slack-800f6bb5111e)
#### 6: [PromQL Query Language](https://prometheus.io/docs/prometheus/latest/querying/basics/)
#### 7: [LogQL Query Language](https://grafana.com/docs/loki/latest/query/)
#### 8: [Grafana Playground](https://play.grafana.org/)
#### 9: [Configure Flask with Prometheue](https://grafana.com/docs/grafana-cloud/monitor-applications/asserts/enable-prom-metrics-collection/application-frameworks/flask/)
#### 10: [Jaeger in Python with OpenTelemetry](https://msalinas92.medium.com/integrating-a-python-api-with-jaeger-using-opentelemetry-3885e0c80db0)
