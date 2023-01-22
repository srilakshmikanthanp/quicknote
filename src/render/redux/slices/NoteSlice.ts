// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { createSlice, PayloadAction } from "@reduxjs/toolkit";

/***********************
 *    Note state       *
 **********************/
interface INoteState {
  note: string;
}

/**********************
 *  Internal Actions  *
 *********************/

const _SetNote = (state: INoteState, action: PayloadAction<string>) => {
  state.note = action.payload;
};

/**********************
 *      Selectors     *
 *********************/

export const selectNote = (state: INoteState) : string => state.note;

/*********************
 *  Slice Creation   *
 ********************/

const noteSlice = createSlice({
  name: "quicknote",
  initialState: {
    note: "",
  } as INoteState,
  reducers: {
    SetNote: _SetNote,
  },
});

/********************
 * Export Reducer   *
 *******************/
export default noteSlice.reducer;

/*******************
 * Export Actions  *
 ******************/

export const { SetNote } = noteSlice.actions;
