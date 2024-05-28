import { createSlice } from "@reduxjs/toolkit";


const initialState = {
    toInputValue: '',
    openToSearchBox: false,
    ccInputValue: '',
    openCcSearchBox: false
}

const findUserOrClientEmailSlice = createSlice({
    name: 'findUserOrClientEmail',
    initialState,
    reducers: {
        setToInputValue(state,action){
            state.toInputValue = action.payload
            if(state.toInputValue.length > 0){
                state.openToSearchBox = true;
                state.openCcSearchBox = false;
                state.ccInputValue = ''
            }else {
                state.openToSearchBox = false;
            }
            
        },
        setCcInputValue(state,action){
            state.ccInputValue = action.payload
            state.openCcSearchBox = state.ccInputValue.length > 0;
        },
        setOpenToSearchBox(state,action){
            state.openToSearchBox = action.payload
        },
        setOpenCcSearchBox(state,action){
            state.openCcSearchBox = action.payload
        }

    }
});

export const findUserOrClientEmailAction = findUserOrClientEmailSlice.actions;

export default findUserOrClientEmailSlice;