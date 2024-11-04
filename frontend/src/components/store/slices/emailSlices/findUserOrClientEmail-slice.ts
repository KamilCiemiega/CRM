import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UserAndClient } from "../../../../interfaces/UserAndClient";
import { addCommaToFiltredInputValue } from "../../../email/emailCreator/findUserOrClientEmail/helperFunctions/addCommaToFltredInputValue";

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
  usersId: number[],
  clientsId: number[]
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
  usersId: [],
  clientsId: []
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
    setSubtitleValue(state, action: PayloadAction<string>) {
      state.subtitleValue = action.payload;
    },
    setUsers(state, action: PayloadAction<UserAndClient[]>) {
      state.users = action.payload;
    },
    setClients(state, action: PayloadAction<UserAndClient[]>) {
      state.clients = action.payload;
    },
    addUserId(state, action: PayloadAction<number>) {
      state.usersId.push(action.payload);
    },
    addClientId(state, action: PayloadAction<number>) {
        state.clientsId.push(action.payload);
    },
  },
});

export const findUserOrClientEmailAction = findUserOrClientEmailSlice.actions;

export default findUserOrClientEmailSlice;
