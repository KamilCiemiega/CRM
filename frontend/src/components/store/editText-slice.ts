import { createSlice, PayloadAction } from "@reduxjs/toolkit";


type ActionType = {
  action: string | null
}

const initialState: ActionType = {
    action: null 
  };

const editTextSlice = createSlice({
  name: "editText",
  initialState,
  reducers: {
    setAction(state, action: PayloadAction<string>) {
      state.action = action.payload;
    },
    clearAction(state) {
      state.action = null;
    }
  },
});

export const editTextAction = editTextSlice.actions;

export default editTextSlice;