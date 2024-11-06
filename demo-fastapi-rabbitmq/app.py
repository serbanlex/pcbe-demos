from fastapi import FastAPI
from starlette.responses import JSONResponse

from producer import send_command
from models import CreateUserCommand, DeleteUserCommand

app = FastAPI(title="Demo - FastAPI with RabbitMQ")

@app.get("/user/{user_id}")
async def get_user(user_id: int):
    return {
        "user_id": user_id
    }

@app.post("/user")
async def create_user(command: CreateUserCommand):
    send_command(command)
    return JSONResponse(
        content={"status": "CreateUserCommand sent to queue", "username": command.username, "email": command.email},
        status_code=201
    )

@app.delete("/user/{user_id}")
async def delete_user(user_id: int):
    command: DeleteUserCommand = DeleteUserCommand(user_id=user_id)
    send_command(command)
    return JSONResponse(
        content={"status": "DeleteUserCommand sent to queue", "user_id": command.user_id},
        status_code=201
    )