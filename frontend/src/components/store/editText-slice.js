import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    action: null,
  };

const editTextSlice = createSlice({
  name: "editText",
  initialState,
  reducers: {
    setAction(state, action) {
      state.action = action.payload;
    },
    clearAction(state) {
      state.action = null;
    },
  },
});

export const editTextAction = editTextSlice.actions;

export default editTextSlice;