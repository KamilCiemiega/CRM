import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UserAndClient } from "../../../../interfaces/UserAndClient";

interface ErrorState {
  to: boolean;
  cc: boolean;
}

interface State {
  trimValue: string;
  toInputValue: string;
  openToSearchBox: boolean;
  ccInputValue: string;
  openCcSearchBox: boolean;
  fieldErrorState: ErrorState;
  theSameUserInInput: boolean;
  subtitleValue: string;
  users: UserAndClient[];
  clients: UserAndClient[];
}

const initialState: State = {
  trimValue: "",
  toInputValue: "",
  openToSearchBox: false,
  ccInputValue: "",
  openCcSearchBox: false,
  fieldErrorState: { to: false, cc: false },
  theSameUserInInput: false,
  subtitleValue: "",
  users: [],
  clients: [],
};

const findUserOrClientEmailSlice = createSlice({
  name: "findUserOrClientEmail",
  initialState,
  reducers: {
    setToInputValue(
      state,
      action: PayloadAction<{ value: string; valutType: string }>
    ) {
      const payload = action.payload;
      let toInputValue = state.toInputValue;
        
        if (payload.valutType === "filtredValue") {
            if (!toInputValue.includes(payload.value)) {
              const lastCommaIndex = toInputValue.lastIndexOf(",");
              const beforeLastComma = toInputValue.substring(0, lastCommaIndex + 1);
              const afterLastComma = toInputValue.substring(lastCommaIndex + 1);
    
              const updatedAfterLastComma = afterLastComma.replace(state.trimValue, '');
              toInputValue = beforeLastComma + updatedAfterLastComma;
    
              toInputValue += payload.value;
            } else {
                state.theSameUserInInput = true;
            }
        } else {
            toInputValue = payload.value;
        }

        state.toInputValue = toInputValue;

        if(toInputValue.length > 0){
            state.openToSearchBox = true;
            state.openCcSearchBox = false;
            state.ccInputValue = "";
        }else {
        state.openToSearchBox = false;
        }
    },
    setCcInputValue(state, action: PayloadAction<string>) {
        state.ccInputValue = action.payload;
        state.openCcSearchBox = state.ccInputValue.length > 0;
      },
    setValueToTrim(state, action: PayloadAction<string>) {
      state.trimValue = action.payload;
    },
    setTheSameUserAlert(state , action: PayloadAction<boolean>){
        state.theSameUserInInput = action.payload
    },
    setOpenToSearchBox(state, action: PayloadAction<boolean>) {
      state.openToSearchBox = action.payload;
    },
    setOpenCcSearchBox(state, action: PayloadAction<boolean>) {
      state.openCcSearchBox = action.payload;
    },
    setFieldErrorState(state, action: PayloadAction<Partial<ErrorState>>) {
      state.fieldErrorState = { ...state.fieldErrorState, ...action.payload };
    },
    setSubtitleValue(state, action: PayloadAction<string>) {
      state.subtitleValue = action.payload;
    },
    setUsers(state, action: PayloadAction<UserAndClient[]>) {
      state.users = action.payload;
    },
    setClients(state, action: PayloadAction<UserAndClient[]>) {
      state.clients = action.payload;
    },
  },
});

export const findUserOrClientEmailAction = findUserOrClientEmailSlice.actions;

export default findUserOrClientEmailSlice;
