import socket
import json


"""
Client application
Receive student ID from user, then query server
and show corresponding information, in a loop.
"""

MAX_ID_LEN = 64
MAX_HEAD_LEN = 4
MAX_BUF = 4096

# Server location
SERVER_PORT = 8000
SERVER_ADDR = 'localhost'


def create_socket():
    """
    Create a socket and connect it to the server
    """

    sock = socket.socket(
        socket.AF_INET,
        socket.SOCK_STREAM
    )

    sock.connect((SERVER_ADDR, SERVER_PORT))
    return sock


def send(sock, id):
    """
    Send id to server

    Raise ValueError if the id's size is large than MAX_ID_LEN
    """

    id = id.encode()

    # id will be sent ONCE
    if len(id) > MAX_ID_LEN:
        raise ValueError(f'ID {id} is larger than MAX_ID_LEN={MAX_ID_LEN}')

    sent = sock.send(id)
    if sent == 0:
        raise RuntimeError('Socket connection broken')


def recv(sock):
    """
    Receive and return student information
    Raw student info is stored in JSON
    Socket is closed after receiving
    """

    # Receive data length first
    length = int.from_bytes(sock.recv(MAX_HEAD_LEN), 'big')

    # Then the data
    chunks = []
    recd = 0

    while recd < length:
        chunk = sock.recv(MAX_BUF)
        if chunk == b'':
            raise RuntimeError('Socket connection broken')
        else:
            chunks.append(chunk)
            recd = recd + MAX_BUF

    sock.close()

    return json.loads(b''.join(chunks))


def get_info(id):
    """
    Get student information with id
    """

    sock = create_socket()
    send(sock, id)
    return recv(sock)


def run():
    while True:
        try:
            print('Enter student ID: ', end='')
            id = input()

            info = get_info(id)
            if info is None:
                print('The entered ID does not match any student')
            else:
                print(f'The entered ID matches this student: \n\tName: {info["name"]}\tAge: {info["age"]}')
        except ValueError as ve:
            print(ve)
        except KeyboardInterrupt:
            print('\nQuit client')
            break


if __name__ == '__main__':
    run()
