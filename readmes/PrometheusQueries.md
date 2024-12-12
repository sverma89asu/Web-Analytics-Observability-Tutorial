# Prometheus Queries
## For Spring Boot services
1. Count number of uris giving 404 response <br />
`count(http_server_requests_seconds_count{status="404"})`
2. Get the apis for job **grocery**, instance **grocery:8080** and uri is not the **prometheus** (for **grocery**) <br />
`http_server_requests_seconds_count{job="grocery", instance="grocery:8080", uri!="/actuator/prometheus"}`
3. Find the GET requests that throw **NoSuchGroceryItemException** <br />
`http_server_requests_seconds_count{exception="NoSuchGroceryItemException", method="GET"}`
4. Find the number of error logs <br />
`logback_events_total{level="error"}`
5. Count number of requests received in the last 1 hour other than **/prometheus** <br />
`floor(sum(increase(http_server_requests_seconds_count{uri!="/actuator/prometheus"}[1h])))`
6. Count number of requests received giving 404 response in last 1 hour <br />
`floor(sum(increase(http_server_requests_seconds_count{status="404"} [1h])))`
7. Show heap memory used in bytes <br />
`jvm_memory_used_bytes{area="heap"}`
8. Find the number of requests per second (including /prometheus) <br />
`rate(http_server_requests_seconds_count [1d])`
9. Show system and process CPU usage <br />
`system_cpu_usage`
`process_cpu_usage`
10. Average request duration per second for a time range of 5 minutes <br />
`sum(rate(http_server_requests_seconds_sum[5m])) / sum(rate(http_server_requests_seconds_count[5m]))`
11. Count the number of errors <br />
`sum(http_server_requests_seconds_count{status=~"4..|5.."})`
12. Memory used bytes by area <br />
`sum(jvm_memory_used_bytes) by (area)`
13. Number of requests for different response status <br />
`sum(http_server_requests_seconds_count{uri!="/actuator/prometheus"}) by (status)`
14. Current number of threads by state <br />
`sum(jvm_threads_states_threads) by (state)`
15. Group request by job and instance <br />
`sum(http_server_requests_seconds_count) by (job, instance)`
16. Group request by job and status <br />
`sum(http_server_requests_seconds_count) by (job, status)`
17. Get logback events by job (for **booktown**) <br />
`logback_events_total{job="booktown"}`
18. Get information about specific URI <br />
`http_server_requests_seconds_count{uri="/api/groceries"}`
19. Use regex in the query
`http_server_requests_seconds_count{uri=~"/api/.*"}`

## For Flask service
1. For flask service, get the information about endpoints <br />
`flask_http_request_duration_seconds_count`