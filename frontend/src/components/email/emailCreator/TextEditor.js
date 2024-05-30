import React, { useState, useEffect } from "react";
import { Editor, EditorState, RichUtils, convertToRaw } from "draft-js";
import { Box } from "@mui/material";
import "../../../style/TextEditor.css";


const TextEditor = () => {
  const [editorState, setEditorState] = useState(EditorState.createEmpty());

  const handleEditorChange = (newEditorState) => {
    setEditorState(newEditorState);
  };

  const toggleInlineStyle = (style) => {
    setEditorState(RichUtils.toggleInlineStyle(editorState, style));
  };

  const toggleBlockType = (blockType) => {
    setEditorState(RichUtils.toggleBlockType(editorState, blockType));
  };

  const undo = () => {
    setEditorState(EditorState.undo(editorState));
  };

  const redo = () => {
    setEditorState(EditorState.redo(editorState));
  };

  return (
    <Box sx={{ height: "65%" }}>
      <Editor editorState={editorState} onChange={handleEditorChange} />
    </Box>
  );
};

export default TextEditor;
