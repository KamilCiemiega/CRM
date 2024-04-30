import { createSlice } from "@reduxjs/toolkit";
import { EditorState, RichUtils } from "draft-js";

const initialState = {
    editorState: EditorState.createEmpty()
}

const editTextSlice = createSlice({
    name:"editText",
    initialState,
    reducers: {
        setEditorState(state, action) {
            state.editorState = action.payload;
        },
        setBoldText(state){
            state.editorState = RichUtils.toggleInlineStyle(state.editorState, 'BOLD');
        },
        setItlicText(state){
            state.editorState = RichUtils.toggleInlineStyle(state.editorState, 'ITALIC');
        },
        setUnderlineText(state){
            state.editorState = RichUtils.toggleInlineStyle(state.editorState, 'UNDERLINE');
        },
        setUndo(state) {
            const newEditorState = EditorState.undo(state.editorState);
            state.editorState = newEditorState;
        },
        setRedo(state) {
            const newEditorState = EditorState.redo(state.editorState);
            state.editorState = newEditorState;
        },
        setBulletList(state) {
            state.editorState = RichUtils.toggleBlockType(state.editorState, 'unordered-list-item');
        },
        setNumberedList(state) {
            state.editorState = RichUtils.toggleBlockType(state.editorState, 'ordered-list-item');
        }
    }
});

export const editTextAction = editTextSlice.actions;

export default editTextSlice;