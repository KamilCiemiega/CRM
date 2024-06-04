import { Typography } from "@mui/material";
import { styled } from "@mui/material/styles";

const StyledTypography = styled(Typography) ({
   padding: '4px',
   transition: 'color 0.3s ease-in-out',
   '&:hover': {
      color: 'white'
  },
}) as typeof Typography


export default StyledTypography ;
