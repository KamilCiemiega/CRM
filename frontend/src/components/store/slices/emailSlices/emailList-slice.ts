import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Message } from "../../../../interfaces/interfaces";

interface State {
    messages: Message[];
    filtredMessages: Message[];
    primaryTabNumber: number;
}

const initialState: State = {
    messages: [],
    filtredMessages: [],
    primaryTabNumber: 1
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
        setFiltredMessages(state, action: PayloadAction<Message[]>){
            state.filtredMessages = action.payload;
        }
    }
});

export const emailListAction = emailListSlice.actions;
export default emailListSlice;
