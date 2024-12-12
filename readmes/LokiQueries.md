1. Get log lines containing **Exception** for grocery-v1 <br />
`{service_name="grocery-v1"} |= "Exception"`

2. Get logs based on level **ERROR** <br />
`{level="ERROR"} |= "Exception"`

3. Use or conditions to find logs matching either of the conditions <br />
`{service_name="grocery-v1"} |= "NoSuchGroceryItemException" or "NullPointerException"`

4. Number of **WARN** or **ERROR** logs in a given time <br />
`count_over_time({app="grocery-v1"} [5m] |= "WARN" or "ERROR")`

5. Find **ERROR** logs in **grocery-v2** service <br />
`{service_name="grocery-v2", level="ERROR"} |= ""`