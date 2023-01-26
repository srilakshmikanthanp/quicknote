// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { configureStore } from "@reduxjs/toolkit";
import rootReducer from "../slices";

// Redux Store Create
const store = configureStore({
  reducer: rootReducer
});

// Export Redux Store
export default store;
