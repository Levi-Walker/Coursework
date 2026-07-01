import json
from dataclasses import dataclass
from enum import Enum, auto
from typing import Optional

class MessageType(Enum):
    REGISTER = auto()
    REGISTER_RESPONSE = auto()
    CHAT = auto()
    ACK = auto()
    UNREGISTER = auto()

@dataclass
class Message:
    type: MessageType
    sender_name: str
    message_id: Optional[int] = None
    content: Optional[str] = None
    
    def to_json(self) -> str:
        return json.dumps({
            'type': self.type.name,
            'sender_name': self.sender_name,
            'message_id': self.message_id,
            'content': self.content
        })
    
    @classmethod
    def from_json(cls, json_str: str) -> 'Message':
        data = json.loads(json_str)
        return cls(
            type=MessageType[data['type']],
            sender_name=data['sender_name'],
            message_id=data['message_id'],
            content=data['content']
        )
