import { AuthService } from './auth.service';
import { RegisterDto, LoginDto } from './dto/auth.dto';
export declare class AuthController {
    private readonly authService;
    constructor(authService: AuthService);
    register(dto: RegisterDto): Promise<{
        message: string;
        user: {
            id: string;
            email: string;
            fullName: string;
            avatarUrl: string | null;
        };
        accessToken: string;
    }>;
    login(dto: LoginDto): Promise<{
        message: string;
        user: {
            id: string;
            email: string;
            fullName: string;
            avatarUrl: string | null;
        };
        accessToken: string;
    }>;
    getProfile(req: any): Promise<{
        email: string;
        fullName: string;
        id: string;
        avatarUrl: string | null;
        createdAt: Date;
    }>;
}
