from locust import HttpUser, task, constant_throughput

class MyUser(HttpUser):
    wait_time = constant_throughput(0.1)

    # @task(1)
    # def get_all(self):
    #     self.client.get("/customers")

    @task(1)
    def create_customer(self):
        self.client.post("/customers", json={"name": "Locust User", "address": "123 Main St"})

    @task(2)
    def get_customer(self):
        self.client.get("/customer?name=John%20Smith")
