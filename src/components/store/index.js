import { configureStore } from '@reduxjs/toolkit';
import emailCreatorSlice from './emailCreator-slice';

const store = configureStore({
  reducer: { emailCreator: emailCreatorSlice.reducer },
});

export default store;