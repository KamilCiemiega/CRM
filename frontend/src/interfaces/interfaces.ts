import { ReactElement } from "react";
import { ParticipantData } from "../components/email/emailList/hooks/useParticipantData";

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

export interface MessageRole {
  id: number;
  status: "TO" | "CC";
  participantId: number;
  email: string | null;
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
  
  interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    roleDTO: any;
  }

  interface MessageFolder {
    id: number;
    name: string;
    parentFolder: any;
    user: User;
    folderType: string;
  }

export interface Message {
    id: number;
    subject: string;
    body: string;
    sentDate: string;
    status: string;
    size: number;
    attachments: [];
    messageFolders: MessageFolder[];
    messageRoles: MessageRole[];
    unlinked: boolean;
}  

export interface Rows {
    id: number;
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