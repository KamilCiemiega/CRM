import { createSlice } from "@reduxjs/toolkit";
import { EditorState, RichUtils, convertToRaw } from "draft-js";

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
            const newEditorState = RichUtils.toggleInlineStyle(state.editorState, 'BOLD');
            state.editorState = convertToRaw(newEditorState.getCurrentContent());
        },
        setItalicText(state){
            const newEditorState = RichUtils.toggleInlineStyle(state.editorState, 'ITALIC');
            state.editorState = convertToRaw(newEditorState.getCurrentContent());
        },
        setUnderlineText(state){
            const newEditorState = RichUtils.toggleInlineStyle(state.editorState, 'UNDERLINE');
            state.editorState = convertToRaw(newEditorState.getCurrentContent());
        },
        setUndo(state) {
            const newEditorState = EditorState.undo(state.editorState);
            state.editorState = convertToRaw(newEditorState.getCurrentContent());
        },
        setRedo(state) {
            const newEditorState = EditorState.redo(state.editorState);
            state.editorState = convertToRaw(newEditorState.getCurrentContent());
        },
        setBulletList(state) {
            const newEditorState = RichUtils.toggleBlockType(state.editorState, 'unordered-list-item');
            state.editorState = convertToRaw(newEditorState.getCurrentContent());
        },
        setNumberedList(state) {
            const newEditorState = RichUtils.toggleBlockType(state.editorState, 'ordered-list-item');
            state.editorState = convertToRaw(newEditorState.getCurrentContent());
        }
    }
});

export const editTextAction = editTextSlice.actions;

export default editTextSlice;