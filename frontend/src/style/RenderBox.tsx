import { Box } from "@mui/material";

type RenderBoxProps = {
  openCcSearchBox: boolean;
  children: React.ReactNode;
};

const RenderBox = ({ openCcSearchBox, children }: RenderBoxProps) => {
  return (
    <Box
      component="section"
      sx={{
        height: "auto",
        background: "#1976d2",
        width: "93%",
        position: "absolute",
        top: openCcSearchBox ? "214px" : "145px",
        left: "52px",
        cursor: "pointer",
        boxShadow: "4px 16px 24px -5px rgba(66, 68, 90, 1)",
        display: "flex",
        alignItems: "center",
        justifyContent: "flex-start",
        zIndex: 1,
        padding: "1px",
      }}
    >
      {children}
    </Box>
  );
};

export default RenderBox;