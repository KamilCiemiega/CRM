import { Typography } from "@mui/material";
import { styled } from "@mui/material/styles";

const StyledTypography = styled(Typography)(({ theme }) => ({
   padding: '4px',
   transition: 'color 0.3s ease-in-out',
   '&:hover': {
      color: 'white'
  },
}));


export default StyledTypography ;
