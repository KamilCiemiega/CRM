import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UserAndClient, MessageRoles } from "../../../../interfaces/interfaces";
import { addCommaToFiltredInputValue } from "../../../email/emailCreator/findUserOrClientEmail/helperFunctions/addCommaToFltredInputValue";

interface ErrorState {
  to: boolean;
  cc: boolean;
}

interface State {
  trimValue: string;
  toInputValue: any;
  openToSearchBox: boolean;
  ccInputValue: any;
  openCcSearchBox: boolean;
  fieldErrorState: ErrorState;
  theSameUserInInput: boolean;
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
  users: [],
  clients: []
};

const findUserOrClientEmailSlice = createSlice({
  name: "findUserOrClientEmail",
  initialState,
  reducers: {
    setToInputValue(state, action) {
      const { value, theSameUserInInput, openSearchBox } = addCommaToFiltredInputValue(
        state.toInputValue,
        action.payload,
        state.trimValue
      );

      state.toInputValue = value;
      state.theSameUserInInput = theSameUserInInput;
      state.openToSearchBox = openSearchBox;
    },
    setCcInputValue(state, action) {
      const { value, theSameUserInInput, openSearchBox } = addCommaToFiltredInputValue(
        state.ccInputValue,
        action.payload,
        state.trimValue
      );

      state.ccInputValue = value;
      state.theSameUserInInput = theSameUserInInput;
      state.openCcSearchBox = openSearchBox;
    },
    setValueToTrim(state, action: PayloadAction<string>) {
      state.trimValue = action.payload;
    },
    setTheSameUserAlert(state, action: PayloadAction<boolean>) {
      state.theSameUserInInput = action.payload;
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
    setUsers(state, action: PayloadAction<UserAndClient[]>) {
      state.users = action.payload;
    },
    setClients(state, action: PayloadAction<UserAndClient[]>) {
      state.clients = action.payload;
    },
    batchUpdate(state, action: PayloadAction<{
      toInputValue?: string;
      ccInputValue?: string;
      openToSearchBox?: boolean;
      openCcSearchBox?: boolean;
    }>) {
      const payload = action.payload;
        state.toInputValue = payload.toInputValue;
        state.ccInputValue = payload.ccInputValue;
      
      if (payload.openToSearchBox !== undefined) {
        state.openToSearchBox = payload.openToSearchBox;
      }
      if (payload.openCcSearchBox !== undefined) {
        state.openCcSearchBox = payload.openCcSearchBox;
      }
    },
  },
});

export const findUserOrClientEmailAction = findUserOrClientEmailSlice.actions;

export default findUserOrClientEmailSlice;
