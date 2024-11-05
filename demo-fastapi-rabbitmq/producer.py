import pika
from models import BaseCommand
import json

def send_command(command: BaseCommand):
    connection = pika.BlockingConnection(
        pika.ConnectionParameters('localhost')
    )
    channel = connection.channel()
    channel.queue_declare(queue='command_queue')

    message = {
        'command_type': type(command).__name__,
        'data': command.model_dump()
    }
    message_json = json.dumps(message)

    channel.basic_publish(
        exchange='',
        routing_key='command_queue',
        body=message_json
    )
    connection.close()