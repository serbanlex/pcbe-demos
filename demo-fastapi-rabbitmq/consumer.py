import pika
import json
from models import CreateUserCommand, DeleteUserCommand

def handle_create_user(command_data):
    command = CreateUserCommand(**command_data)
    print(f"Creating user: {command.username}, email: {command.email}")

def handle_delete_user(command_data):
    command = DeleteUserCommand(**command_data)
    print(f"Deleting user with ID: {command.user_id}")

def callback(ch, method, properties, body):
    message = json.loads(body)
    command_type = message.get('command_type')
    command_data = message.get('data')

    if command_type == 'CreateUserCommand':
        handle_create_user(command_data)
    elif command_type == 'DeleteUserCommand':
        handle_delete_user(command_data)
    else:
        print(f"Unknown command type: {command_type}")

connection = pika.BlockingConnection(
    pika.ConnectionParameters('localhost')
)
channel = connection.channel()
channel.queue_declare(queue='command_queue')

channel.basic_consume(
    queue='command_queue',
    on_message_callback=callback,
    auto_ack=True
)

print('Waiting for commands. To exit press CTRL+C')
channel.start_consuming()