import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Message } from "../../../../interfaces/interfaces";

interface State {
    messages: Message[];
    filtredMessages: Message[];
    primaryTabNumber: number;
    secondaryTabNumber: number | null;
    clickedCheckboxes: string[];
}

const initialState: State = {
    messages: [],
    filtredMessages: [],
    primaryTabNumber: 1,
    secondaryTabNumber: null,
    clickedCheckboxes: []
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
        setSecondaryTabNumber(state, action: PayloadAction<number>){
            state.secondaryTabNumber = action.payload;
        },
        setFiltredMessages(state, action: PayloadAction<Message[]>){
            state.filtredMessages = action.payload;
        },
        setClickedChecboxes(state, action: PayloadAction<string[]>){
            state.clickedCheckboxes = action.payload;
        }
    }
});

export const emailListAction = emailListSlice.actions;
export default emailListSlice;
