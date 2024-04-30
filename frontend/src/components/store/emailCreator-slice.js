import { createSlice } from "@reduxjs/toolkit";


const initialState = {
    openDialog: false
};

const emailCreatorSlice = createSlice({
    name: "emailCreator",
    initialState,
    reducers: {
        setOpenDialog(state, action){
            state.openDialog = action.payload
        }
    }
});

export const emailCreatorAction = emailCreatorSlice.actions;

export default emailCreatorSlice;