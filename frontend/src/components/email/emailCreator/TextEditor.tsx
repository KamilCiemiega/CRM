import { useState, useEffect } from "react";
import { Editor, EditorState, RawDraftContentState, RichUtils, convertFromRaw, convertToRaw } from "draft-js";
import { useSelector, useDispatch } from "react-redux";
import { Box } from "@mui/material";
import "../../../style/TextEditor.css";
import { editTextAction } from "../../store/slices/emailSlices/editText-slice";
import { sendEmailAction } from "../../store/slices/emailSlices/sendEmail-slice";
import { RootState } from "../../store";

const TextEditor = () => {
  const [editorState, setEditorState] = useState(EditorState.createEmpty());
  const action = useSelector((state:RootState) => state.editText.action);
  const fetchedBodyState = useSelector((state: RootState) => state.emailPreview.dataToDisplay.body);
  const dispatch = useDispatch();

  useEffect(() => {
    const loadSavedContent = () => {
      try{
        const rawContent: RawDraftContentState = JSON.parse(fetchedBodyState);
        const contentState = convertFromRaw(rawContent);
        setEditorState(EditorState.createWithContent(contentState));
      } catch (error) {
        console.log("Parsing data error", error)
      }
      
    };
    loadSavedContent();
  }, [fetchedBodyState]);

  useEffect(() => {
    if (action) {
      let newEditorState = editorState;
      switch (action) {
        case "BOLD":
          newEditorState = RichUtils.toggleInlineStyle(editorState, "BOLD");
          break;
        case "ITALIC":
          newEditorState = RichUtils.toggleInlineStyle(editorState, "ITALIC");
          break;
        case "UNDERLINE":
          newEditorState = RichUtils.toggleInlineStyle(editorState, "UNDERLINE");
          break;
        case "UNDO":
          newEditorState = EditorState.undo(editorState);
          break;
        case "REDO":
          newEditorState = EditorState.redo(editorState);
          break;
        case "unordered-list-item":
          newEditorState = RichUtils.toggleBlockType(editorState, "unordered-list-item");
          break;
        case "ordered-list-item":
          newEditorState = RichUtils.toggleBlockType(editorState, "ordered-list-item");
          break;
        default:
          break;
      }

      setEditorState(newEditorState);
      dispatch(editTextAction.clearAction());
    }
  }, [action, editorState, dispatch]);

  const handleEditorChange = (newEditorState: EditorState) => {
    setEditorState(newEditorState);
    const contentState = newEditorState.getCurrentContent();
    const rawContent = convertToRaw(contentState);
    dispatch(sendEmailAction.setEditorContent(rawContent));
  };

  return (
    <Box sx={{ height: "59%" }}>
      <Editor 
        editorState={editorState} 
        onChange={handleEditorChange}
      />
    </Box>
  );
};

export default TextEditor;
