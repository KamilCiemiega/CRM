import { createSlice } from "@reduxjs/toolkit";
import { EditorState, RichUtils, convertToRaw } from "draft-js";

const initialState = {
    editorState: EditorState.createEmpty()
}

const editTextSlice = createSlice({
    name: "editText",
    initialState,
    reducers: {
        setEditorState(state, action) {
            state.editorState = action.payload;
        },
        setBoldText(state) {
            const newEditorState = RichUtils.toggleInlineStyle(state.editorState, 'BOLD');
            state.editorState = newEditorState;
        },
        setItalicText(state) {
            const newEditorState = RichUtils.toggleInlineStyle(state.editorState, 'ITALIC');
            state.editorState = newEditorState;
        },
        setUnderlineText(state) {
            const newEditorState = RichUtils.toggleInlineStyle(state.editorState, 'UNDERLINE');
            state.editorState = newEditorState;
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
            const newEditorState = RichUtils.toggleBlockType(state.editorState, 'unordered-list-item');
            state.editorState = newEditorState;
        },
        setNumberedList(state) {
            const newEditorState = RichUtils.toggleBlockType(state.editorState, 'ordered-list-item');
            state.editorState = newEditorState;
        }
    }
});

export const selectEditorTextAndStyles = (state) => {
    const content = state.editText.editorState.getCurrentContent();
    const rawContent = convertToRaw(content);
    let styledText = [];

    rawContent.blocks.forEach(block => {
        let blockText = block.text;
        let lastOffset = 0;

        block.inlineStyleRanges.forEach(styleRange => {
            if (styleRange.offset > lastOffset) {
                styledText.push({
                    text: blockText.slice(lastOffset, styleRange.offset),
                    styles: []
                });
            }
      
            styledText.push({
                text: blockText.slice(styleRange.offset, styleRange.offset + styleRange.length),
                styles: [styleRange.style]
            });
            lastOffset = styleRange.offset + styleRange.length;
        });

        if (lastOffset < blockText.length) {
            styledText.push({
                text: blockText.slice(lastOffset),
                styles: []
            });
        }
    });

    return styledText;
}

export const editTextAction = editTextSlice.actions;

export default editTextSlice;