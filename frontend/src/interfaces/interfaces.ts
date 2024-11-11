import { ReactElement } from "react";
import { ParticipantData } from "../hooks/useParticipantData";

export interface UserAndClient {
    id: number;
    firstName: string;
    lastName: string;
    password: string;
    email: string;
    clients: string[];
    messages: string[];
    folders: string [];
}

export interface MessageRoles {
    status: string;
    participantId: number
}

export interface SendMessageStatus {
    status:string;
    message: string;
    openAlert: boolean
}

export interface NavigationItemProps {
    icon: ReactElement;
    primary: string;
    index: number;
    openItems: number[];
    activeItem: number;
    handleClick: (index: number) => void;
    collapseItems: { index: number; icon: ReactElement; primary: string }[];
  }

  interface MessageRole {
    id: number;
    status: "TO" | "CC";
    participantId: number;
  }

export interface Message {
    id: number;
    subject: string;
    body: string;
    sentDate: string;
    status: string;
    size: number;
    attachments: [];
    messageFolder: [];
    messageRoles: MessageRole[];
}  

export interface Rows {
    id: number;
    status: string;
    subject: string;
    sendDate: string;
    size: number;
}

export interface DataToDisplay {
    body: string,
    subtitle: string,
    attachmentsNumber: number,
    participant:ParticipantData[]
}