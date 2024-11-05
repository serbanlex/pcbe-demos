from pydantic import BaseModel

class BaseCommand(BaseModel):
    pass

class CreateUserCommand(BaseCommand):
    username: str
    email: str

class DeleteUserCommand(BaseCommand):
    user_id: int