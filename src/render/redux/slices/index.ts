// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import noteReducer, { SetNote, selectNote } from "./NoteSlice";
import { combineReducers } from "@reduxjs/toolkit";

// combine reducers
const rootReducer = combineReducers({
  quicknote: noteReducer,
});

// export reducer
export default rootReducer;

// export actions
export { SetNote };

// export selectors
export { selectNote }