
 const fetchData = async (): Promise<Client[]> => {
            const response = await axios.get("http://localdev:8082/api/clients");
            if (response.status === 200 && Array.isArray(response.data)) {
                return response.data as Client[];
            } else {
                console.warn(`Unexpected response format or status: ${response.status}`);
                return [];
            }
};

type PropsValues<T> = {
    url: string;
    value: T;
    getData?: boolean;
}


//deklaracje funkcji
const test = (test: string, test2: number) => {}
const sendData = async <T>({url, value, getData}: PropsValues<T>) => {}

const clickedEntityData = useSelector((state: RootState) => state.clientView.clickedEntity);

//Przekazywanie funkcji collback jak props
interface SearchMessagesTableProps {
    onSearch: (filtredMessages: FiltredMessage[]) => void;
}
const SearchEmails: React.FC<SearchMessagesTableProps> = ({onSearch}) => {}

interface ChildProps {
    name: string;
    age: number;
}
const ChildComponent: React.FC<ChildProps> = ({ name, age }) => {}


const [ clientMessages, setClientMessages ] = useState<Message[]>([])
useEffect(() => {
    const timer = setTimeout(() => {
      setRequestError(false);
    }, 3000);

    return () => clearTimeout(timer);
  }, [requestError]);


const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormValues((prev) => ({ ...prev, [name]: value }));
};
value={formValues[field.name as keyof typeof formValues]}
phone: 'phoneNumber' in clickedEntityData ? clickedEntityData.phoneNumber : clickedEntityData.phone,
errorMessage: typeof axiosError.response?.data === 'string' ? axiosError.response.data : "An error occurred",
interface Navigator {
    navigate: (path: string) => void;
}

// Record<keyof EntityFields, string>:
// Tworzy nowy typ, gdzie każdy klucz z EntityFields (name, surname, itd.) jest przypisany do wartości typu string.
//Zawija powyższy typ w Partial, co oznacza, że wszystkie klucze są opcjonalne.
type ValidationErrors = Partial<Record<keyof EntityFields, string>>;

const statusMap: { [key: number]: string } = {    
    1: "INBOX",
    8: "SENT",
    12: "DRAFT",
    17: "FAVORITE",
    22: "TRASH"
};

statusMap[typeOfTab];


useEffect(() => {
    const timer = setTimeout(() => {
      setRequestError(false);
    }, 3000);

    return () => clearTimeout(timer);
  }, [requestError]);

  useEffect(() => {
    // Tworzymy interwał, który zwiększa licznik co sekundę
    const intervalId = setInterval(() => {
      setSeconds((prev) => prev + 1);
    }, 1000);

    // Czyszczenie interwału
    return () => {
      clearInterval(intervalId);
      console.log('Interval cleared');
    };
  }, []); 