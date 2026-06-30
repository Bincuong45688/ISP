import { NestFactory } from '@nestjs/core';
import { ValidationPipe } from '@nestjs/common';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  // Bật CORS để Frontend có thể gọi API
  app.enableCors({
    origin: ['http://localhost:5173', 'http://localhost:3000', 'https://fe-wedo.vercel.app'],
    credentials: true,
  });

  // Bật validation pipe toàn cục
  app.useGlobalPipes(
    new ValidationPipe({
      whitelist: true,
      forbidNonWhitelisted: true,
      transform: true,
    }),
  );

  await app.listen(process.env.PORT ?? 3000, '0.0.0.0');
  console.log(`🚀 Server đang chạy tại http://0.0.0.0:${process.env.PORT ?? 3000}`);
}
bootstrap();
