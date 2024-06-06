import React, {useState} from "react";
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
import { editTextAction } from "../../../store/slices/emailSlices/editText-slice";
import StyledBox from '../../../../style/EditTextBarStyle'

const EditTextBar = () => {
  const dispatch = useDispatch();
  const [clickedIcon, setClickedIcon] = useState('');

  const iconStyle = {
    marginRight: "4px",
    cursor: "pointer",
  };

  const clickedIconStyle = {
    background: '#bbbbbb',
    marginRight: "4px",
    cursor: "pointer",
  }

  const handleClick = (actionType: string) => {
    dispatch(editTextAction.setAction(actionType));
    setClickedIcon(actionType);
  };

  return (
    <StyledBox>
      <Undo onClick={() => handleClick("UNDO")} style={clickedIcon === 'UNDO' ? clickedIconStyle : iconStyle} />
      <Redo onClick={() => handleClick("REDO")} style={clickedIcon === 'REDO' ? clickedIconStyle : iconStyle} />
      <FormatBold onClick={() => handleClick("BOLD")} style={clickedIcon === 'BOLD' ? clickedIconStyle : iconStyle} />
      <FormatItalic onClick={() => handleClick("ITALIC")} style={clickedIcon === 'ITALIC' ? clickedIconStyle : iconStyle} />
      <FormatUnderlined onClick={() => handleClick("UNDERLINE")} style={clickedIcon === 'UNDERLINE' ? clickedIconStyle : iconStyle} />
      <FormatListBulleted onClick={() => handleClick("unordered-list-item")} style={clickedIcon === 'unordered-list-item' ? clickedIconStyle : iconStyle} />
      <FormatListNumbered onClick={() => handleClick("ordered-list-item")} style={clickedIcon === 'ordered-list-item' ? clickedIconStyle : iconStyle} />
    </StyledBox>
  );
};

export default EditTextBar;