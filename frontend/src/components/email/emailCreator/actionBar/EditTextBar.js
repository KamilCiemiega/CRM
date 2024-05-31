import React from "react";
import {
  Undo,
  Redo,
  FormatBold,
  FormatItalic,
  FormatUnderlined,
  FormatListBulleted,
  FormatListNumbered,
} from "@mui/icons-material";
import { useDispatch } from "react-redux";
import { editTextAction } from "../../../store/editText-slice";
import StyledBox from '../../../../style/EditTextBarStyle'

const EditTextBar = () => {
  const dispatch = useDispatch();

  const iconStyle = {
    marginRight: "4px",
    cursor: "pointer",
  };

  const handleClick = (actionType) => {
    dispatch(editTextAction.setAction(actionType));
  };

  return (
    <StyledBox>
      <Undo onClick={() => handleClick("UNDO")} style={iconStyle} />
      <Redo onClick={() => handleClick("REDO")} style={iconStyle} />
      <FormatBold onClick={() => handleClick("BOLD")} style={iconStyle} />
      <FormatItalic onClick={() => handleClick("ITALIC")} style={iconStyle} />
      <FormatUnderlined onClick={() => handleClick("UNDERLINE")} style={iconStyle} />
      <FormatListBulleted onClick={() => handleClick("unordered-list-item")} style={iconStyle} />
      <FormatListNumbered onClick={() => handleClick("ordered-list-item")} style={iconStyle} />
    </StyledBox>
  );
};

export default EditTextBar;