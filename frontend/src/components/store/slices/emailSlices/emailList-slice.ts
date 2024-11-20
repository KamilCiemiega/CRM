import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Message } from "../../../../interfaces/interfaces";

interface FolderMessageRequestStatus {
    status: string;
    message: string;
}

interface State {
    messages: Message[];
    filtredMessages: Message[];
    primaryTabNumber: number;
    secondaryTabNumber: number | null;
    clickedCheckboxes: string[];
    changeFolderMessageRequestStatus: FolderMessageRequestStatus
}

const initialState: State = {
    messages: [],
    filtredMessages: [],
    primaryTabNumber: 1,
    secondaryTabNumber: null,
    clickedCheckboxes: [],
    changeFolderMessageRequestStatus: {status: "", message: ""}
};

const emailListSlice = createSlice({
    name: "emailList",
    initialState,
    reducers: {
        setAllMessages(state, action: PayloadAction<Message[]>) {
            state.messages = action.payload;
        },
        setPrimaryTabNumber(state, action: PayloadAction<number>){
            state.primaryTabNumber = action.payload;
        },
        setSecondaryTabNumber(state, action: PayloadAction<number | null>){
            state.secondaryTabNumber = action.payload;
        },
        setFiltredMessages(state, action: PayloadAction<Message[]>){
            state.filtredMessages = action.payload;
        },
        setClickedChecboxes(state, action: PayloadAction<string[]>){
            state.clickedCheckboxes = action.payload;
        },
        setResetCheckboxes(state, action: PayloadAction<[]>){
            state.clickedCheckboxes = action.payload;
        },
        setFolderMessageRequestStatus(state: State, action: PayloadAction<FolderMessageRequestStatus>){
            state.changeFolderMessageRequestStatus = action.payload;
        }
    }
});

export const emailListAction = emailListSlice.actions;
export default emailListSlice;
