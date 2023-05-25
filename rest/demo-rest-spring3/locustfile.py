from locust import HttpUser, task, constant_throughput

class MyUser(HttpUser):
    wait_time = constant_throughput(0.1)

    @task
    def my_task(self):
        self.client.get("/customers")
