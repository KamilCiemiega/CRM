import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Message, DataToDisplay } from "../../../../interfaces/interfaces";
import { ParticipantData } from "../../../../hooks/useParticipantData";
import { MessageRole } from "../../../email/emailList/mainEmailFunctions/TableDataComponent";

interface State {
   clickedMessage: Message; 
   dataToDisplay: DataToDisplay;
   showMessagePreview: boolean;
   shouldShowPreview: boolean;
   messageRoles: MessageRole[]
}

const initialState: State = {
    clickedMessage:{
        id: 0, 
        subject: "", 
        body: "",
        sentDate:"",
        status: "",
        size: 0,
        attachments: [],
        messageFolder: [],
        messageRoles: []
    },
    dataToDisplay: {
        body: "",
        subtitle:"",
        attachmentsNumber: 0,
        participant: []
    },
    showMessagePreview: false,
    shouldShowPreview: false,
    messageRoles: []
};

const emailPreviewSlice = createSlice({
    name: "emailPreview",
    initialState,
    reducers: {
        setClickedMessage(state: State, action: PayloadAction<Message>){
            state.clickedMessage = action.payload;
        },
        setDataToDisplay(state: State, action: PayloadAction<ParticipantData[] | {body: string, subtitle: string, attachmentsNumber: number}>) {
            if (Array.isArray(action.payload)) {
                state.dataToDisplay.participant = action.payload;
            }else {
                state.dataToDisplay.body = action.payload.body;
                state.dataToDisplay.subtitle = action.payload.subtitle;
                state.dataToDisplay.attachmentsNumber = action.payload.attachmentsNumber;
            }
        },
        setMessagePreview(state: State, action: PayloadAction<boolean>){
            state.showMessagePreview = action.payload;
        },
        setShouldShowPreview(state: State, action: PayloadAction<boolean>) {
            state.shouldShowPreview = action.payload;
        },
        setMessageRoles(state: State, action: PayloadAction<MessageRole[]>) {
            state.messageRoles = action.payload;
        },
    }
});

export const emailPreviewAction = emailPreviewSlice.actions;
export default emailPreviewSlice;
