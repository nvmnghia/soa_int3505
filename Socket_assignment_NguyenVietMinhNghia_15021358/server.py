import socket
import json
from threading import Thread


"""
Server application
Receive student ID from client, then return
corresponding information.
"""


MAX_ID_LEN = 64
MAX_HEAD_LEN = 4
MAX_BUF = 4096

PORT = 8000
ADDR = 'localhost'

STUDENTS = {
    '15021358': {
        'name': 'nvmnghia',
        'age' : '23'
    },
    '15021359': {
        'name': 'nvmtu',
        'age' : '21'
    }
}


def client_thread(s_client):
    """
    Process client request
    Information is dumps as JSON before sending to client
    """

    ip_client, port_client = s_client.getpeername()
    id = s_client.recv(MAX_BUF).decode().strip()
    print(f'Query from {ip_client}:{port_client} for {id}')

    info = json.dumps(STUDENTS.get(id), separators=(',', ':')).encode()

    # Send data length first (considered a LEN header)
    length = len(info)
    s_client.send(length.to_bytes(MAX_HEAD_LEN, 'big'))

    # Send the data (considered the payload)
    s_client.sendall(info)


def client_processing(s_client):
    """
    Wrap client_thread in a thread
    """
    Thread(target=client_thread, args=[s_client]).start()


def run():
    s_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s_server.bind((ADDR, PORT))    # Not visible outside anyway, no need to gethostname()
    s_server.listen()

    while True:
        try:
            s_client, address = s_server.accept()
            client_processing(s_client)
        except KeyboardInterrupt:
            print('\nQuit server')
            break


if __name__ == '__main__':
    run()
