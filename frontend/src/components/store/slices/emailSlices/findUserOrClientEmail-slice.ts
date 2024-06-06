import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface FieldErrorState {
    to: boolean;
    cc: boolean;
  }

  interface UserAndClient {
    firstName: string;
    lastName: string;
    password: string;
    email: string;
    clients: string[];
    messages: string[];
    folders: string [];
  }

  interface State {
    toInputValue: string;
    openToSearchBox: boolean;
    ccInputValue: string;
    openCcSearchBox: boolean;
    fieldErrorState: FieldErrorState;
    subtitleValue: string;
    users: UserAndClient[];
    clients: UserAndClient[];
  }

const initialState: State = {
    toInputValue: '',
    openToSearchBox: false,
    ccInputValue: '',
    openCcSearchBox: false,
    fieldErrorState: {to: false, cc: false},
    subtitleValue: '',
    users: [],
    clients: []
}

const findUserOrClientEmailSlice = createSlice({
    name: 'findUserOrClientEmail',
    initialState,
    reducers: {
        setToInputValue(state,action: PayloadAction<string>){
            state.toInputValue = action.payload
            if(state.toInputValue.length > 0){
                state.openToSearchBox = true;
                state.openCcSearchBox = false;
                state.ccInputValue = ''
            }else {
                state.openToSearchBox = false;
            }
            
        },
        setCcInputValue(state,action: PayloadAction<string>){
            state.ccInputValue = action.payload
            state.openCcSearchBox = state.ccInputValue.length > 0;
        },
        setOpenToSearchBox(state,action: PayloadAction<boolean>){
            state.openToSearchBox = action.payload;
        },
        setOpenCcSearchBox(state,action: PayloadAction<boolean>){
            state.openCcSearchBox = action.payload;
        },
        setFieldErrorState(state, action: PayloadAction<Partial<FieldErrorState>>) {
            state.fieldErrorState = { ...state.fieldErrorState, ...action.payload };
        },
        setSubtitleValue(state, action: PayloadAction<string>){
            state.subtitleValue = action.payload;
        },
        setUsers(state, action: PayloadAction<UserAndClient[]>) {
            state.users = action.payload;
        },
        setClients(state, action: PayloadAction<UserAndClient[]>) {
            state.clients = action.payload;
        }
    }
});

export const findUserOrClientEmailAction = findUserOrClientEmailSlice.actions;

export default findUserOrClientEmailSlice;