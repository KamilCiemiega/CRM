import { useState } from "react";
import "../../../../style/EditTexBar.css";
import {
  Undo,
  Redo,
  FormatSize,
  FormatBold,
  FormatItalic,
  FormatUnderlined,
  FormatColorText,
  FormatListBulleted,
  FormatListNumbered,
} from "@mui/icons-material";

const iconStyle = {
  marginRight: "4px",
  cursor: "pointer",
};
const activeIconStyle = {
  ...iconStyle,
  backgroundColor: "rgba(32,33,36,0.122)",
  borderRadius: "3px",
};

const EditTextBar = () => {
  const [activeIcon, setActiveicon] = useState(" ");

  return (
    <div id="container">
      <Undo
        sx={activeIcon === "Undo" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("Undo")}
      />
      <Redo
        sx={activeIcon === "Redo" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("Redo")}
      />
      <FormatSize
        sx={activeIcon === "FormatSize" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("FormatSize")}
      />
      <FormatBold
        sx={activeIcon === "FormatBold" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("FormatBold")}
      />
      <FormatItalic
        sx={activeIcon === "FormatItalic" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("FormatItalic")}
      />
      <FormatUnderlined
        sx={activeIcon === "FormatUnderlined" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("FormatUnderlined")}
      />
      <FormatColorText
        sx={activeIcon === "FormatColorText" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("FormatColorText")}
      />
      <FormatListBulleted
        sx={activeIcon === "FormatListBulleted" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("FormatListBulleted")}
      />
      <FormatListNumbered
        sx={activeIcon === "FormatListNumbered" ? activeIconStyle : iconStyle}
        onClick={() => setActiveicon("FormatListNumbered")}
      />
    </div>
  );
};

export default EditTextBar;
