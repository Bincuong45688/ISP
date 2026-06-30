import { JwtService } from '@nestjs/jwt';
import { PrismaService } from '../prisma/prisma.service';
import { RegisterDto, LoginDto } from './dto/auth.dto';
export declare class AuthService {
    private readonly prisma;
    private readonly jwtService;
    constructor(prisma: PrismaService, jwtService: JwtService);
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
    getProfile(userId: string): Promise<{
        email: string;
        fullName: string;
        id: string;
        avatarUrl: string | null;
        createdAt: Date;
    }>;
    private generateToken;
}
