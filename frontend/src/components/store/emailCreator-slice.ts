import { createSlice, PayloadAction } from "@reduxjs/toolkit";


const initialState = {
    openDialog: false
};

const emailCreatorSlice = createSlice({
    name: "emailCreator",
    initialState,
    reducers: {
        setOpenDialog(state, action: PayloadAction<boolean>){
            state.openDialog = action.payload
        }
    }
});

export const emailCreatorAction = emailCreatorSlice.actions;

export default emailCreatorSlice;