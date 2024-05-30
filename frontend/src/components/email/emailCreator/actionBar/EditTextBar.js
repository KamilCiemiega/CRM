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
  import StyledBox from "../../../style/TextEditorStyle";

const EditTextBar = () => {

    const iconStyle = {
        marginRight: "4px",
        cursor: "pointer",
      };

    const handleClick = () => {

    }

return(
    <StyledBox>
      <Undo onClick={undo} style={iconStyle} />
        <Redo onClick={redo} style={iconStyle} />
        <FormatBold
          
          style={iconStyle}
        />
        <FormatItalic
          
          style={iconStyle}
        />
        <FormatUnderlined
          
          style={iconStyle}
        />
        <FormatListBulleted
          
          style={iconStyle}
        />
        <FormatListNumbered
          
          style={iconStyle}
        />
        </StyledBox>
);

}

export default EditTextBar;