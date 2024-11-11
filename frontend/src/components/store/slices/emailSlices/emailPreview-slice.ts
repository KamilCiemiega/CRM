import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { DataToDisplay } from "../../../../interfaces/interfaces";
import { ParticipantData } from "../../../../hooks/useParticipantData";
import { MessageRole } from "../../../email/emailList/TableDataComponent";

interface State {
   dataToDisplay: DataToDisplay;
   showMessagePreview: boolean;
   shouldShowPreview: boolean;
   messageRoles: MessageRole[]
}

const initialState: State = {
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
        setShouldShowPreview(state: State, action: PayloadAction<boolean>) { // Dodana nowa akcja
            state.shouldShowPreview = action.payload;
        },
        setMessageRoles(state: State, action: PayloadAction<MessageRole[]>) { // Nowy reducer do ustawienia messageRoles
            state.messageRoles = action.payload;
        },
    }
});

export const emailPreviewAction = emailPreviewSlice.actions;
export default emailPreviewSlice;
