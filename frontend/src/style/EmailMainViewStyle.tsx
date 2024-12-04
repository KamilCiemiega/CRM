import { Box, BoxProps } from "@mui/material";

const StyledBox = (props: BoxProps) => (
  <Box
    sx={{
      width: '100%',
      height: '100vh',
      display: "flex",
      overflow: "hidden"
    }}
    {...props}
  />
);

export default StyledBox;