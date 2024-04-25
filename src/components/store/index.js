  import { configureStore } from '@reduxjs/toolkit';
  import emailCreatorSlice from './emailCreator-slice';
  import editTextSlice from './editText-slice';

  const store = configureStore({
    reducer: { emailCreator: emailCreatorSlice.reducer, editText: editTextSlice.reducer },
  });

  export default store;